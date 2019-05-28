Program design: All java files can be found in src/edu/osu/cse
The parse folder holds the java files for the parser and the token folder holds the java files for the tokenizer.
The tokenizer:
	GrammerToken.java: set the complete grammer for CORE has been given in the course notes.
	Tokenizer.java: get the tokens from the input stream and displays the token number given above to the output stream one number per line
The parser:
  	Parse.java - The main process, it parses a input file and output a parse tree.
  	ParserException.java - It handles the error input
  	BaseNode.java - This is the base object. It defines the basic behave of all Node.
  		Prog.java, DeclSeq.java, StmtSeq.java, Decl.java, IdList.java, Stmt.java, Assign.java, IfNode.java, Loop.java,In.java, Out.java, Cond.java, Comp.java, Exp.java, Term.java, Fac.java, Comp_op.java, Id.java, Int_node.java, Rw_node.java, Ss_node.java -
		each java files creates the node type of each of following nodes:prog, decl_seq, stmt_seq, decl, id_list, stmt, assign, if_node, loop, in, out, cond, comp, exp, term, fac, comp_op, id, int_node, rw_node, ss_node

The documentation is in the doc folder

To compile:
javac Tokenizer.java
it will generate all class files. and jar. 

To run the program:
java -jar jars/Parse.jar testfilename
