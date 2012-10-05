(ns remotecraft.core
  (:use compojure.core
        [clojure.tools.logging :only (info error)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [remotecraft.actions :as act]
            ))

(def q (atom clojure.lang.PersistentQueue/EMPTY))

(defroutes main-routes

  (GET "/test" {m :params}
       (do (Thread/sleep 1000)
         (str "a: " (:a m) " b: " (:b m) " c: " (:c m))))

  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (handler/site main-routes))

(defn queue [act]
  (swap! q (fn [old] (conj old act))))
  
