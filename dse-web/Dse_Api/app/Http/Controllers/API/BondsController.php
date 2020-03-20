<?php

namespace App\Http\Controllers\API;


use Illuminate\Http\Request;
use Validator;
use App\Http\Controllers\API\BaseController as BaseController;
use App\Bond;
use App\Http\Resources\Bond as BondResource;


class BondsController extends BaseController
{
        /**

     * Display a listing of the resource.

     *

     * @return \Illuminate\Http\Response

     */

    public function index()

    {

       $bonds = Bond::all();

    

        return $this->sendResponse(BondResource::collection($bonds), 'Bonds retrieved successfully.');

    }

    /**

     * Store a newly created resource in storage.

     *

     * @param  \Illuminate\Http\Request  $request

     * @return \Illuminate\Http\Response

     */

    public function store(Request $request)

    {

        $input = $request->all();
   

        $validator = Validator::make($input, [

            'name' => 'required',

            'price' => 'required',

            'consideration' => 'required'
            

        ]);

   

        if($validator->fails()){

            return $this->sendError('Validation Error.', $validator->errors());       

        }

   

       $bonds = Bond::create($input);

   

        return $this->sendResponse(new BondResource($bonds), 'Bonds created successfully.');

    } 

   

    /**

     * Display the specified resource.

     *

     * @param  int  $id

     * @return \Illuminate\Http\Response

     */

    public function show($id)

    {

       $bonds = Bond::find($id);

  

        if (is_null($bonds)) {

            return $this->sendError('Bond not found.');

        }

   

        return $this->sendResponse(new BondResource($bonds), 'Bond retrieved successfully.');

    }

    

    /**

     * Update the specified resource in storage.

     *

     * @param  \Illuminate\Http\Request  $request

     * @param  int  $id

     * @return \Illuminate\Http\Response

     */

    public function update(Request $request, Bond $bonds)

    {

        $input = $request->all();

   
        $validator = Validator::make($input, [


            'name' => 'required',

            'price' => 'required',

            'consideration' => 'required'


        ]);

   

        if($validator->fails()){

            return $this->sendError('Validation Error.', $validator->errors());       

        }

   
       $bonds->name = $input['name'];

       $bonds->price = $input['price'];

       $bonds->consideration = $input['consideration'];

       $bonds->save();

   

        return $this->sendResponse(new BondResource($bonds), 'Bond updated successfully.');

    }

   

    /**

     * Remove the specified resource from storage.

     *

     * @param  int  $id

     * @return \Illuminate\Http\Response

     */

    public function destroy(Bond $bonds)

    {

       $bonds->delete();

   

        return $this->sendResponse([], 'Bond deleted successfully.');

    }
}
