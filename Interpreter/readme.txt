Program design: All java files can be found in src/edu/osu/cse
The parse folder holds the java files for the parser, the token folder holds the java files for the tokenizer, and the interporater folder holds the java files for the interpreter
The tokenizer:
	GrammerToken.java: set the complete grammer for CORE has been given in the course notes.
	Tokenizer.java: get the tokens from the input stream and displays the token number given above to the output stream one number per line
The parser:
  	Parse.java - The main process, it parses a input file and output a parse tree.
  	ParserException.java - It handles the error input
  	BaseNode.java - This is the base object. It defines the basic behave of all Node.
  		Prog.java, DeclSeq.java, StmtSeq.java, Decl.java, IdList.java, Stmt.java, Assign.java, IfNode.java, Loop.java,In.java, Out.java, Cond.java, Comp.java, Exp.java, Term.java, Fac.java, Comp_op.java, Id.java, Int_node.java, Rw_node.java, Ss_node.java -
		each java files creates the node type of each of following nodes:prog, decl_seq, stmt_seq, decl, id_list, stmt, assign, if_node, loop, in, out, 
The Interporater:
	Interporater.java - This is the control object for interporation.  It track variable defining and verify variable usage. It also maintain a stack to pass variable and value among different node.  
	ComparableNode.java - The base object for Variable and Value. 
	Variable.java - Object which representing idNode, it is wrapper class for integer type of varaible in Java.
	Value.java - Object which representing intNode, it is wrapper class for integer constance in Java. 
	The Operator:
		Operator.java - This is an interface which only defined one method: evaluation(Stack<ComparableNode> va). all sub object will be required to consume elements in Stack, and generate an integer value. This is a mimic object for Java operator.
		AssignOp.java, CompOperator.java, MultiplyOp.java, PlusOp.java, ReaderOp.java, WriterOp.java - Implementation of Operator.

To compile:
make
To run:
java -cp "classes" edu.osu.cse.interporater.Interporater testfile.txt