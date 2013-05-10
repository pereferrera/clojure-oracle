(ns oracle
  (:require clojure.string)
  (:gen-class))

(import 'java.lang.Runtime)
(import 'java.lang.String)

(defn fetch [url]
  (net.ferrerabertran.oracle.Fetcher/fetch url))

(defn encode [partial_url]
  (java.net.URLEncoder/encode partial_url))

(defn facts-of [what]
  (filter (fn [str] (not (or (re-find #"\QSpecial:\E" str) (re-find #"\QHelp:\E" str) (re-find #"\QWikipedia:\E" str))))
          (map (fn [vec] (nth vec 1))
               (re-seq #"\Q/wiki/\E([^\"]*)\""
                       (fetch (str "http://en.wikipedia.org/w/index.php?fulltext=Search&search=" (encode what)))))))       

(defn images-of [name]  
  (map (fn [vec] (nth vec 1))
       (re-seq #"imgurl=([^\&]*)\&" 
               (fetch (str "http://www.google.com/search?tbm=isch&q=" (encode name))))))

(defn videos-of [name]
  (map (fn [vec] (str "http://www.youtube.com/watch?v=" (nth vec 1))) 
       (re-seq #"watch\?v=([^\"]*)\"" 
               (fetch (str "http://www.youtube.com/results?q=" (encode name))))))

(defn fact [wiki-fact]
  (str "http://en.wikipedia.com/wiki/" wiki-fact))

(defn where [place]
  (str "https://maps.google.com/maps?geocode=&q=" (encode place)))

(defn route [origin dest method]
  (let [g-method (cond (= method "walk") "w" :else "w")]
  (str "http://maps.google.es/maps?" "saddr=" (encode origin) "&" "daddr=" (encode dest) "&" "dirflg=" g-method)))

(defn show [url]
  (. (Runtime/getRuntime) exec (str "google-chrome " url)))

(defn ask [question]
  (let [l-question (clojure.string/lower-case question)
        video-matches (re-matches #"(\Qwhich\E|\Qwhat\E)(\Q is that\E|\Q is this\E)(\Q video about \E)([^\?]*)\?" l-question)
        image-matches (re-matches #"(\Qhow \E)(\Qdoes\E|\Qdo\E) ([^\?]*)(\Q look like\E\?)" l-question)
        place-matches (re-matches #"(\Qwhere is \E)([^\?]*)(\?)" l-question)
        route-matches (re-matches #"(\Qhow \E)(\Qdo \E|\Qdoes \E)(\Qi \E|\Qyou \E|\Qone \E|\Qwe \E)(\Qget from \E)([^\?]*)(\Q to \E)([^\?]*)(\?)" l-question)
        facts-matches (re-matches #"(\Qwhat can you tell me about \E)([^\?]*)(\?)" l-question)
        ]
    (cond
      facts-matches (show (fact (first (facts-of (nth facts-matches 2)))))      
      route-matches (show (route (nth route-matches 5) (nth route-matches 7) "walk"))
      video-matches (show (first (videos-of (nth video-matches 4))))
      place-matches (show (where (nth place-matches 2)))
      image-matches (show (first (images-of (nth image-matches 3))))
      :else "Question not understood")))

(defn -main [& args]
  (let [question (clojure.string/join " " args)]
    (println question)
    (println (ask question))))