(ns remotecraft.core
  (:use compojure.core
        [clojure.tools.logging :only (info error)]
        ring.middleware.file)
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [remotecraft.actions :as act]
            [hiccup.core :as hic]
            ))

(def inverter (atom {:unscheduled (promise)
                     :scheduled {:action nil :response nil}}))

(defn call [action]
  (let [return (promise)]
    (deliver (:unscheduled @inverter) {:action action :response return})
    @return))

(defn handle [response]
  (let [old-request (:response (:scheduled @inverter))]
    (if old-request
      (do
        (deliver old-request response)
        (swap! inverter (fn [old] (assoc-in old [:scheduled :response] nil)))))
    (let [new-request (deref (:unscheduled @inverter) 10000 :timeout)]
      (if (= new-request :timeout)
        "wait"
        (do
          (swap! inverter (fn [old]
                          {:scheduled new-request
                           :unscheduled (promise)}))
          (:action new-request))))))

(defroutes main-routes

  (POST "/req" {params :params}
        (do (info (:request params))
            (call (:request params))))

  (POST "/resp" {m :params}
            (handle (:response m)))

  (GET "/" [] (hic/html
[:html
 [:head]
 [:body
  [:form
   [:textarea#text]
   [:input#button {:type "button" :value "execute"}]
   ]
  [:div#response]
  [:script {:type "text/javascript" :src "js/jquery.js"}]
  [:script {:type "text/javascript" :src "js/execute.js"}]
  ]
 ]))
  
  (POST "/tester" {params :params}
        (do (info (:response params))
            (:response params)))

  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (handler/site main-routes))

;;(defn call [act]
;;  (swap! request (fn [old] (request
;;        (deref (:return @request))]
;;    (swap! request (fn [old]
