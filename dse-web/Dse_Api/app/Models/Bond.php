<?php


namespace App\Models;

use Illuminate\Database\Eloquent\Model;


class Bond extends Model

{

    /**

     * The attributes that are mass assignable.

     *

     * @var array

     */

    protected $fillable = [
        'bondnumber','issuer', 'price','duration'

    ];

}
