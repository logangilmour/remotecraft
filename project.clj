(defproject remotecraft "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [
                 [org.clojure/clojure "1.3.0"]
                 [compojure "1.1.0"]
                 [org.clojure/tools.logging "0.2.3"]
                 [hiccup "1.0.1"]
                 ]
  :plugins [
            [lein-ring "0.7.1"]
            ]
  :ring {:handler remotecraft.core/app
  }
  )