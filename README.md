A simple Clojure project that answers quick questions like "How does Lady Gaga in the WC look like?".

Use maven for compiling it. Load oracle.clj in the REPL, and ask a question. Questions you can ask:

	(ask "How does Lady Gaga in the WC look like?")

This will pop-up an image in google.

	(ask "Where is the Berlin wall?")
	
This will pop up a maps page in google maps.

	(ask "Which is that video about a grumpy cat?")
	
This will pop up a video in youtube.
Note: The answers are pop-up using "google-chrome" so you should have it in the path.
