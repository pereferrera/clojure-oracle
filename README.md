A simple Clojure project that answers quick questions like "How does Lady Gaga in the WC look like?".

Use maven for compiling it. Load oracle.clj in the REPL, and ask a question. Questions you can ask:

	(ask "How does Lady Gaga in the WC look like?")

This will pop-up an image in google.

	(ask "Where is the Berlin wall?")
	
This will pop up a maps page in google maps.

	(ask "How do I get from the Brandenburger Tor to Alexanderplatz in Berlin?")
	
This will pop up a route in google maps.

	(ask "Which is that video about a grumpy cat?")
	
This will pop up a video in youtube.

	(ask "What can you tell me about ABBA?")
	
This will pop up a page in wikipedia.
Note: The answers are pop-up using "google-chrome" so you should have it in the path.

You can compile a JAR with dependencies with mvn assembly:assembly and use it as command-line tool. The command-line method will take variable number of parameters and join them with a space. E.g.:

	java -jar target/oracle-0.0.1-jar-with-dependencies.jar How do I get from the Brandenburger Tor to Alexanderplatz in Berlin?
