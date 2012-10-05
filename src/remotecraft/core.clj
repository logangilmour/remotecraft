(ns remotecraft.core
  (:use compojure.core
        [clojure.tools.logging :only (info error)])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            ))

(defroutes main-routes

  (GET "/init" []
       (do (logger/init config/database) "Successfully initialized views."))

  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (handler/site main-routes))

