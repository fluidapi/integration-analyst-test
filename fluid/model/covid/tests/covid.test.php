<?php
error_reporting(0);
require_once dirname(__DIR__) . "/covid.model.php";

function covidTest()
{
    $test_csv_input = file(__DIR__ . "/assets/csv_input.csv");
    $test_json_output = file_get_contents(__DIR__ . "/assets/json_output.json");

    $model_reponse = covidModel($test_csv_input);
    $expected_response = json_decode($test_json_output, true);

    if ($model_reponse == $expected_response) {
        return "PASS";
    } 
    else {
        return "FAIL";
    }
}

echo covidTest();
