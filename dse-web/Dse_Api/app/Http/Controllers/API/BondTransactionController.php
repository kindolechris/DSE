<?php

namespace App\Http\Controllers\API;

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use App\Models\BondsTransaction;
use App\Models\User as user;
use App\Http\Controllers\API\BaseController as BaseController;
use Validator;

class BondTransactionController extends BaseController
{
   

    public function index()
    {
        //
    }

  

    public function create()
    {
        //
    }


    public function store(Request $request)
    {
        $user = Auth::user();
        $input = $request->all();
        $vmoney = $user->virtualmoney;
        $id = $user->id;
        $atPrice = $request->input('atPrice');
        $units = $request->input('units');
        $consideration =   ($atPrice / 100) * $units;

        $input['consideration'] = $consideration;
        $input['userId'] = $id;

        $newVirtualMoney =   $vmoney - $consideration; 

        //
        $validator = Validator::make($request->all(), [

            'bondnumber' => 'required',
            'atPrice' => 'required',
            'units' => 'required',
            'type' => 'required',
            'status' => 'required',

        ]);

        if($validator->fails()){

            return $this->sendError('Validation Error.', $validator->errors());

        }

   
        if($vmoney < $consideration ) {
            return response()->json(["message" => "Virtual money is less"],404);
        }

        $transaction = BondsTransaction::create($input);
        $this->updateUserFunds($newVirtualMoney,$units,$id);

        return response()->json($transaction,201);


    }



    public function show($id)
    {
        //
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

    
    public function updateUserFunds($newVirtualMoney,$newBonds,$id){

        $user = user::find($id);
        $user->virtualmoney = $newVirtualMoney;
        $user->bonds = $newBonds;
        $user->save();
    }
}
