(import 'java.lang.Runtime)

(ns oracle)

(defn fetch [url]
  (net.ferrerabertran.oracle.Fetcher/fetch url))

(defn encode [partial_url]
  (java.net.URLEncoder/encode partial_url))

(defn images_of [name]  
  (map (fn [vec] (nth vec 1)) 
       (re-seq #"imgurl=([^\&]*)\&" 
               (fetch (str "http://www.google.com/search?tbm=isch&q=" (encode name))))))

(defn show [url]
  (. (Runtime/getRuntime) exec (str "/usr/bin/google-chrome " url)))

(defn ask [question]
  (show (first (images_of (nth (re-matches #"(\QHow does \E)([^\?]*)(\Q look like\E\?)" question) 2)))))