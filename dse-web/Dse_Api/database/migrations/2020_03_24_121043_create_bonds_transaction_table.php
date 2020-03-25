<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateBondsTransactionTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('bonds_transaction', function (Blueprint $table) {
            $table->id();
            $table->integer('bondnumber');
            $table->integer('atPrice');
            $table->integer('units');
            $table->double('consideration');
            $table->string('type');
            $table->integer('userId');
            $table->string('status');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('bonds_transaction');
    }
}
