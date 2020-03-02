
 <nav class="navbar navbar-expand-lg navbar-light fixed-top py-3" id="mainNav">
            <div class="container-fluid">
                <a class="navbar-brand" href="{{ url('/') }}">
                    <img src="{{Asset('images/logo.png')}}" style="height:60px;"/>
                </a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="{{ __('Toggle navigation') }}">
                    <span class="navbar-toggler-icon"></span>
                </button>


                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <!-- Left Side Of Navbar -->
                    <ul class="navbar-nav m-auto">
                    <li class="nav-item">
                                <a class="nav-link" href=""><h4 style="font-family:'Gotham Rounded Medium'">{{ __('Home') }}</h4></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href=""><h4 style="font-family:'Gotham Rounded Medium'">{{ __('About') }}</h4></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href=""><h4 style="font-family:'Gotham Rounded Medium'">{{ __('News') }}</h4></a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href=""><h4 style="font-family:'Gotham Rounded Medium'">{{ __('Contact') }}</h4></a>
                            </li>

                    </ul>

                    <!-- Right Side Of Navbar -->
                    <ul class="navbar-nav ml-auto">
                        <!-- Authentication Links -->
                        @guest
                            <li class="nav-item">
                                <a class="nav-link" href="{{ route('login') }}"><h4 style="font-family:'Gotham Rounded Medium'">{{ __('Login') }}</h4></a>
                            </li>
                            @if (Route::has('register'))
                                <li class="nav-item">
                                    <a class="nav-link" href="{{ route('login') }}"><h4 style="font-family:'Gotham Rounded Medium'">{{ __('Register') }}</h4></a>
                                </li>
                            @endif
                        @else
                            <li class="nav-item dropdown">
                                <a id="navbarDropdown" class="nav-link dropdown-toggle" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" v-pre>
                                    {{ Auth::user()->name }} <span class="caret"></span>
                                </a>

                                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                                    <a class="dropdown-item" href="{{ route('logout') }}"
                                       onclick="event.preventDefault();
                                                     document.getElementById('logout-form').submit();">
                                        {{ __('Logout') }}
                                    </a>

                                    <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">
                                        @csrf
                                    </form>
                                </div>
                            </li>
                        @endguest
                    </ul>
                </div>
            </div>
        </nav>