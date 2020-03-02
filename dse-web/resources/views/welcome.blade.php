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



        <div class="row">

          <div class="col-md-12 col-lg-12">
            <h5 class="text-center mb-2 font-weight-bold" style="font-family:'poppins'">Welcome to DSE scholarship investment challenge</h5>
            <p class="text-center mb-4 font-weight-normal" style="font-family:'poppins'">
              Here's what you need to know to get started.
            </p>
          </div>
        </div>
    <div class="container-fluid">
  <div class="row">
                  <div class="col-md-12  col-lg-4">

                    <div class="card mb-3 border-none shadow">
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
                  <div class="col-md-12 col-lg-4">

                    <div class="card mb-3 border-none shadow">
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

                    <div class="card mb-3 border-none shadow">
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

  <div class= "container position-relative">
    <div class="row">
    <div class="col-md-12 col-lg-6">
      <img class="img-responsive"src="{{asset('images/phone.png')}}">
    </div>
    <div class="col-md-12 col-lg-6">
      <h3 class="text-center">Go Mobile With Our App</h3>
    </div>
  </div>


  </div>
  @endsection
