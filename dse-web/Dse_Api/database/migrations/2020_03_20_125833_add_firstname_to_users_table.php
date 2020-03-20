<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class AddFirstnameToUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::table('users', function (Blueprint $table) {
            //
            $table->string('firstname')->after('id');
            $table->string('lastname')->after('firstname');
            $table->string('gender')->after('lastname');
            $table->string('tradername')->after('gender');
            $table->string('phonenumber')->after('tradername');
            $table->string('university')->after('phonenumber');
            $table->string('yearOfStudy')->after('university');
            $table->string('coursename')->after('yearOfStudy');
            $table->string('bonds')->after('email');
            $table->string('stock')->after('bonds');
            $table->string('virtualmoney')->after('stock');
            $table->string('role')->after('virtualmoney');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::table('users', function (Blueprint $table) {
            //
        });
    }
}
