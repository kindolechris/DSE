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
    <section>

    </section>
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
                        <h5 class="card-title text-center font-weight-bold">Invest</h5>
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
                        <h5 class="card-title text-center font-weight-bold">Grow Portfolio</h5>
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
                        <h5 class="card-title text-center font-weight-bold">Make Profits</h5>
                        <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                       <div class="text-center">
                        <a href="#" class="btn btn-info">Go somewhere</a>
                        </div>
                      </div>
                    </div>


                  </div>

    </div>
    <div class="card shadow-lg container-fluid mt-5">
    <div class="row">
      <div class="col-md-12 col-lg-5">
        <img class="img-fluid phone-img"src="{{asset('images/phone.png')}}">
      </div>
      <div class="col-md-12 col-lg-6">
        <h3 class="text-center mt-5 font-weight-bold">Go Mobile With Our App</h3>
        
        <div class="row text-center mt-3">
          <div class="col-lg-4 d-none d-lg-inline d-lg-none"><i class="fa fa-universal-access fa-2x"></i>
            <h5 class="text-center mt-2">Quick Access</h5>
          </div>
           <div class="col-lg-4 d-none d-lg-inline d-lg-none"><i class="fa fa-rocket fa-2x"></i>
             <h5 class="text-center mt-2">Intuitive UI</h5></div>
            <div class="col-lg-4 d-none d-lg-inline d-lg-none"><i class="fa fa-life-ring fa-2x"></i>
              <h5 class="text-center mt-2">Fast Support</h5></div>
        </div>
        <p class="text-justify">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
        tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
        quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
        consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
        cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
        proident, sunt in culpa qui officia deserunt mollit anim id est laborum</p>
        <p class="text-justify"> Ut enim ad minim veniam,
        quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
        consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse
        cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
        proident, sunt in culpa qui officia deserunt mollit anim id est laborum</p>

        <div class="text-md-center text-sm-center text-lg-left"><a href="#" class="btn btn-clear" style="border: none !important;"><img class="img-fluid" src="{{asset('images/btn.png')}}" width="250" /> </a>
        </div>

      </div>
    
    </div>
  </div>

    <footer class="footer">
      <div class="container text-center">
        <span class="text-muted">&copy;2020 Development | Powered by <a href="https://www.dse.co.tz/" target="_blank">DSE</a> | All Rights Reserved. </span>
      </div>
    </footer>
    </div>
    </div>
  </div>
  @endsection
