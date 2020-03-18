package com.example.myapplication

class Korona {

    var country : String? = null
    var cases : Int?=0
    var todayCases : Int?=0
    var deaths : Int?=0
    var todayDeaths : Int?=0
    var recovered : Int?=0
    var active : Int?=0
    var critical : Int?=0

    constructor(){

    }

    constructor(country:String,cases:Int,todayCases:Int,deaths:Int,todayDeaths:Int,recovered:Int,active:Int,critical:Int){
        this.country = country
        this.cases = cases
        this.todayCases = todayCases
        this.deaths = deaths
        this.todayDeaths = todayDeaths
        this.recovered = recovered
        this.active = active
        this.critical = critical

    }







}