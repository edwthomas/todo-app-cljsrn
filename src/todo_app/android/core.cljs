(ns todo-app.android.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [todo-app.events]
            [todo-app.subs]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def navigator (r/adapt-react-class (.-Navigator ReactNative)))
(def view-pager-android (r/adapt-react-class (.-ViewPagerAndroid ReactNative)))

(def logo-img (js/require "./images/cljs.png"))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(defn welcome-scene
  [route navigator]
  (let [greeting (subscribe [:get-greeting])]
    (fn []
      [view-pager-android {:style        {:flex 1 :flex-direction "column"}
                           :initial-page 0}
       [view {:style {:align-items "center"}}
        [text {:style {:font-size 30 :font-weight "100" :margin-bottom 40 :text-align "center"}} @greeting]

        [text {:style {:font-size 20 :margin 30}} "Swipe right for ViewPager demo"]

        [touchable-highlight {:style    {:background-color "#999" :padding 10 :border-radius 5}
                              :on-press #(alert "Not yet implemented")}
         [text {:style {:color "white" :text-align "center" :font-weight "bold"}} "Skip Intro"]]]

       [view {:style {:align-items "center"}}
        [text {:style {:font-size 20 :font-weight "100" :margin 100 :text-align "center"}}
         "Swipe right for more of this awesome ViewPager demo!"]

        [text "Or go back to the welcome page"]]

       [view {:style {:align-items "center"}}
        [text {:style {:font-size 20 :font-weight "100" :margin 100 :text-align "center"}} "Done with ViewPager demo!"]

        [touchable-highlight {:style    {:background-color "#999" :padding 10 :border-radius 5}
                              :on-press #(alert "Not yet implemented")}
         [text {:style {:color "white" :text-align "center" :font-weight "bold"}} "Add ToDo"]]]])))

;; Use multimethod instead of cond?
(defn scene
  "Choose the appropriate scene from the given route"
  [route navigator]
  (let [active-scene (:scene (js->clj route :keywordize-keys true))]
    (cond
      (= active-scene "welcome") (r/as-element [welcome-scene route navigator])
      :else (r/as-element [welcome-scene route navigator]))))

(defn app-root []
  "Gets the active screen and sets the React Native navigator"
  (let [active-scene (subscribe [:get-active-scene])]
    [navigator {:initial-route-stack [{:scene @active-scene}]
                :render-scene        (fn [route navigator] (scene route navigator))}]))

(defn init []
  (dispatch-sync [:initialize-db])
  (.registerComponent app-registry "TodoApp" #(r/reactify-component app-root)))
