<?php
require_once dirname(__DIR__) . "/model/covid/covid.model.php";
function covidController($request_file){
    return covidModel($request_file);
}