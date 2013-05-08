(import 'java.lang.Runtime)
(require 'clojure.string)
 
(ns oracle)

(defn fetch [url]
  (net.ferrerabertran.oracle.Fetcher/fetch url))

(defn encode [partial_url]
  (java.net.URLEncoder/encode partial_url))

(defn images-of [name]  
  (map (fn [vec] (nth vec 1)) 
       (re-seq #"imgurl=([^\&]*)\&" 
               (fetch (str "http://www.google.com/search?tbm=isch&q=" (encode name))))))

(defn videos-of [name]
  (map (fn [vec] (str "http://www.youtube.com/watch?v=" (nth vec 1))) 
       (re-seq #"watch\?v=([^\"]*)\"" 
               (fetch (str "http://www.youtube.com/results?q=" (encode name))))))

(defn where [place]
  (str "https://maps.google.com/maps?geocode=&q=" (encode place)))

(defn show [url]
  (. (Runtime/getRuntime) exec (str "google-chrome " url)))

(defn ask-where [question]
  (where (nth  2)))

(defn ask [question]
  (let [l-question (clojure.string/lower-case question)
        video-matches (re-matches #"(\Qwhich\E|\Qwhat\E)(\Q is that\E|\Q is this\E)(\Q video about \E)([^\?]*)\?" l-question)
        image-matches (re-matches #"(\Qhow \E)(\Qdoes\E|\Qdo\E) ([^\?]*)(\Q look like\E\?)" l-question)
        place-matches (re-matches #"(\Qwhere is \E)([^\?]*)(\?)" l-question)] 
    (cond
      video-matches (show (first (videos-of (nth video-matches 4))))
      place-matches (show (where (nth place-matches 2)))
      image-matches (show (first (images-of (nth image-matches 3))))
      :else "Question not understood")))
