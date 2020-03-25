<?php

namespace App\Http\Controllers\API;
use App\Http\Controllers\API\BaseController as BaseController;
use Illuminate\Http\Request;
use App\Models\Bond;
use Validator;
use Illuminate\Support\Facades\Auth;

class BondsController extends BaseController
{

    public function index()
    {
        //
        return response()->json(Bond::get(),200);
    }


    public function create()
    {
        //

        
    }

 
    public function store(Request $request)
    {
        //
   


        $input = $request->all();

        $validator = Validator::make($input, [

            'bondnumber' => 'required',

            'issuer' => 'required',

            'price' => 'required',

            'duration' => 'required'


        ]);


        if($validator->fails()){

            return $this->sendError('Validation Error.', $validator->errors());

        }



       $bonds = Bond::create($input);

        return response()->json($bonds,201);

    }

  
    public function show($id)
    {
        //
        $bond = Bond::find($id);
        if(is_null($bond)){
            return response()->json(["message" => "Record not found"],404);
        }
        return response()->json($bond,200);

    }

    public function edit($id)
    {
        //
    }


    public function update(Request $request, $id)
    {
        //
    }

  
    public function destroy($id)
    {
        //
    }


}
