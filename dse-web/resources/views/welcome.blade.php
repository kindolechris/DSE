@extends('layouts.app')

@section('content')
  <!-- Page Header -->

<header class="masthead" style="background-image: url('{{ asset('images/11.png') }}')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
        </div>
      </div>
    </div>
  </header>
  <div class="container-fluid">
    <div class="round-box bg-white box-shadow py-5 px-4 px-sm-5">

      <div>

        <div class="row">
  
          <div class="col-md-12 col-lg-12">
            <h5 class="text-center mb-4">Hello Everyone</h5>
  
  
  
  
          </div>
        </div>
    <div class="container">
  <div class="row">
                  <div class="col-md-12 col-lg-4">
  
                    <div class="card mb-3 mr-3 border-none shadow" style="width: 30rem;">
                      <img class="card-img-top" src="{{asset('images/invest.png')}}" style="height:220px" alt="Card image cap">
                      <div class="card-body">
                        <h5 class="card-title text-center">Invest</h5>
                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                        <div class="text-center">
                        <a href="#" class="btn btn-info">Go somewhere</a>
                        </div>
                      </div>
                    </div>
  
  
                  </div>
                  <div class="col-md-12  col-lg-">
  
                    <div class="card mb-3 border-none shadow" style="width: 30rem;">
                      <img class="card-img-top " src="{{asset('images/portfolio.png')}}" style="height:220px" alt="Card image cap">
                      <div class="card-body">
                        <h5 class="card-title text-center">Grow Portfolio</h5>
                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                        <div class="text-center">
                        <a href="#" class="btn btn-info">Go somewhere</a>
                        </div>
                      </div>
                    </div>
  
  
                  </div>
                  <div class="col-md-12 col-lg-4">
  
                    <div class="card mb-3 mr-3 border-none shadow" style="width: 30rem;">
                      <img class="card-img-top" src="{{asset('images/grow.png')}}" style="height:220px" alt="Card image cap">
                      <div class="card-body">
                        <h5 class="card-title text-center">Make Profits</h5>
                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                       <div class="text-center">
                        <a href="#" class="btn btn-info">Go somewhere</a>
                        </div>
                      </div>
                    </div>
  
  
                  </div>
  
  
  
  
      </div>
    </div>
    </div>
    </div>
  </div>
  @endsection
