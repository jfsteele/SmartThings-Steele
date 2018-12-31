/*
 *  Copyright 2019 John Steele
 *
 *  Provides a virtual lock "switch" for status (updated via WebCoRE)`.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */

metadata {
    definition (name: "Virtual Lock Switch", namespace: "jfsteele", author: "jfsteele") {
        capability "Actuator"
        capability "Sensor"
        capability "Switch"
        capability "Indicator"

        attribute "battery", "number"
        attribute "actor", "string"

        command "setBatteryLevel", ["number"]
        command "setActor", ["string"]
    }

    preferences {}

    tiles(scale: 2) {
        multiAttributeTile(name:"switch", type: "lighting", width: 6, height: 4, canChangeIcon: true){
            tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "on", label:'Locked', action:"switch.off", icon:"st.Home.home30", backgroundColor:"#00A0DC", nextState:"turningOff"
                attributeState "off", label:'Unlocked', action:"switch.on", icon:"st.Home.home30", backgroundColor:"#FFFFFF", nextState:"turningOn", defaultState: true
                attributeState "turningOn", label:'Locking', action:"switch.off", icon:"st.Home.home30", backgroundColor:"#00A0DC", nextState:"turningOn"
                attributeState "turningOff", label:'Unlocking', action:"switch.on", icon:"st.Home.home30", backgroundColor:"#FFFFFF", nextState:"turningOff"
            }
        }

        valueTile("batteryLevel", "device.battery", width: 2, height: 2, decoration: "flat") {
            state "default", label:'Battery Level\n${currentValue}%', defaultState: true, backgroundColor: "#ffffff"
        }
        valueTile("actor", "device.actor", width: 3, height: 2, decoration: "flat") {
            state "default", label:'Actor:\n${currentValue}', defaultState: true, backgroundColor: "#ffffff"
        }

        main(["switch"])
//        details(["switch"])
        details(["switch", "batteryLevel", "actor"])

    }
}

def parse(String description) {
}

def on() {
    sendEvent(name: "switch", value: "on", isStateChange: true)
}

def off() {
    sendEvent(name: "switch", value: "off", isStateChange: true)
}

def setBatteryLevel(Number level) {
    sendEvent(name: "battery", value: level)
}

def setActor(String who) {
    sendEvent(name: "actor", value: who)
}

