Ishay Dahan id 204078547(ishaydah) and Dor Ziv 203992003(dorzi)

First note:
	make sure al the jobs' jar are found in the S3 bucket.

Local application: named 'ExtractCollations':
	this application will be ran from a local computer. it receives 4 arguments as inputs:
	minimum npmi, minimal relative npmi, heb/eng (desired corpus), 1/0 (1 is to filter stop words).

Job flow:
Job1:
	input: get the corpus
	map output: for each bi-gram:
		1) key->(left,1), value->(bi-gram,year, occurences, isLeft=true).
		2) key->(left,2), value-> (bi-gram,year, occurences, isLeft=true).
		3) key->(right,1), value->(bi-gram,year, occurences, isLeft=false).
		4) key->(right,2), value->(bi-gram,year, occurences, isLeft=false).
	output:  for each bi-gram (w_1, w_2, left occ of w_1, 0, year, total occ of w_1,w_2), (w_1, w_2, 0, right occ of w_2, year, total occ of w_1,w_2).
	
	This job's purpose is to count the occurences for each word in the bi-gram as a 'left word' and the occurences
	as a 'right word'.
	we will notice that at the sorting stage first the '1's will be sorted and thats when the counter will sum, and
	when a '2' will be shown we will know that we past all the '1's and now we need to save to context.
	It will generate two shows for each bi-gram. one show which has the occ of w_1 as a left word, and '0' as the occ
	of w_2 as a right word, and the second show is the opposite but for w_2.
	
Job2:
	input: Job1 output.
	map output:for each bi-gram:
		key-> (w_1,w_2, left occ, right occ, year), value-> total occurences
	output: unions Job1 output of each bi-gram: (w_1, w_2, left occ of w_1, right occ of w_2, year, total occ of w_1,w_2).
	
	This Job's purpose is to union the shows of a bi-gram.it sums the occurences of w_1 as a left word, and the occurences
	of w_2 as a right word, and generates a new show which includes the total sums, as mentioned in this job's output.
	
Job3:
	input: Job2 output.
	map output: for each bi-gram:
		1)key->(w_1,w_2), value-> (bi-gram, leftOcc, rightOcc, totalOcc, year)
		2)key-> (*,*), value-> (bi-gram, leftOcc, rightOcc, totalOcc, year)
	output: for each bi-gram : (w_1,w_2, year, npmi).
	
	This job's main purpose is to find the number of all bi-grams in the corpus. we mark the num as N. 
	we will notice that in this job's 'for loop', all the stars will be summed before the real 
	bi-gram, because '*' value is lower than any 'abc' letter. after that we will be able to 
	calculate the npmi for each bi-gram, because the N would be known already.
	
Job4:
	input: Job 3 output
	map output: for each bi-gram:
		1) key->(w_1,w_2), value->(bi-gram, year, npmi)
		2) key->(*,*), value->(bi-gram, year, npmi)
	output: for each bi-gram : (w_1,w_2, year, npmi).
	
	This job's first purpose is to filter  job's 3 output. it calculates the relative npmi for each bi-gram, and then if 
	it is smaller the given minimal relative npmi, or if it's npmi is smaller than the given minimal npmi  it will be filtered.
	the second purpose it to write the output to the context in descending matter.
	
Good and Bad Examples list:
  filtered examples:
	1) the bi-gram "I notice" has been filtered because 'I' is declared as an english stop word.
		(we didnt differ between lower or upper case).
	
	2) the bi-gram =" 555 st" has been filtered because we defined that if a word is not from A-Za-z than it is a stop word.
  
    3) the bi-grams "Robert means", "energy solution" and "Since bright","בזמן לבית","דווקא יהודים" are filtered because their npmi or
		relative npmi is smaller than the given values.
  unfiltered examples:
	1) the bi-gram "body weight" hasn't been filtered because both words are from A-Za-z and doesn't contains any char/ stop words.
	
	2) the bi-gram "fuel gold" hasn't been filtered from the same reason above.

Over all, we filtered every collocation which wasn't made of A-Za-z or contained characters, or if the min npmi or min relative npmi
are smaller than the given values.