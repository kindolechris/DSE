<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/


Route::post('register', 'API\RegisterController@register');

Route::post('login', 'API\RegisterController@login');

/*
Route::get('bonds', 'API\BondsController@bonds');
Route::get('showBond/{id}', 'API\BondsController@showBond');
Route::post('addBond', 'API\BondsController@addBond');
Route::put('updateBond/{id}', 'API\BondsController@updateBond');
*/

Route::middleware('auth:api')->group( function () {


    Route::apiResource('bond','API\BondsController');
    Route::apiResource('bondtransaction','API\BondTransactionController');
});


