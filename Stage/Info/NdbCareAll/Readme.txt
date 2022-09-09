Open the web documentations for Ndbcare
#######################################

Step 1 : Download the folder NdbCareWeb 

Step 2 : The opend this file : docs > build > html > index.html

And that's it !


Update the web documentations for Ndbcare - For Mac
###################################################

Step 1 : Download the folder NdbCareWeb, the package NdbCare and the Script installSphinxMac.sh and put them in the samle folder.

Step 2 : Open your command shell and put yoursefl in the same folder of Step 1 (with cd ).

Step 3 : Install sphinx thanks to the script installSphinxMac.sh by using this command : 
			
			>>> chmod 775 installSphinxMac.sh
			>>> source installSphinxMac.sh

NB     : If your are in the good directory, you should have no error and an hmtl page should be opened.

		 ( if you have some warning here with type docstring, it’s okay, you can continue the steps )


Step 4 : So here any updates in the description of the functions of NdbCare package will appear after running the command  make html
	     If you add new functions to an existing module with the description then you have to add this line in this file :

		NbdCareWeb > docs > source > module_name.rst
		
		The line to add : .. autofunction:: NdbCare.module_name.new_function_name
		
		Run the command make html ( always by putting youself in the folder docs of NdbCareWeb thanks to command line)


Step 5 : Then open this file  to see the updates : docs > build > html > index.html