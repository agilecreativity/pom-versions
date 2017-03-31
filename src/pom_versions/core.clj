(ns pom-versions.core
  (:require [clojure.data.xml :refer [parse-str] :as xml]
            [clojure.zip :as zip]))

(defn get-versions
  "Returns a vector of known versions of a given library from the pom-metadata.xml file.

Example of such file is http://repo1.maven.org/maven2/org/seleniumhq/selenium/selenium-java/maven-metadata.xml"
  ([maven-metadata]
   (let [zipped-xml (zip/xml-zip (xml/parse-str maven-metadata))
         parsed-xml (->
                     zipped-xml
                     first
                     :content
                     last
                     last
                     last
                     (nth 2)
                     last
                     last)]
     (->>
      parsed-xml
      (map #(:content %))
      flatten
      sort
      vec))))
