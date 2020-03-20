@extends('layouts.app')

@section('content')
  <!-- Page Header -->

  <div class="navigation">
    <input type="checkbox" class="navigation__checkbox" id="navi-toggle">
    <a href="{{route('index')}}" for="navi-toggle" class="navigation__button"><span class="navigation__iconback"><i class="casualicon icon-basic-home"></i></span></a>

</div>
<main>
 <section class="section-center">
    <div class="row">
        <div class="book">
            <div class="book__form">
                <form id="regForm" action="/action_page.php">
                    <div class="u-margin-bottom-medium">
                        <h2 class="heading-secondary">
                           Login
                        </h2>
                    </div>

                    <div class="form__group">
                        <input type="email" class="form__input" placeholder="Email address" id="email" autocomplete="new-email" required>
                        <label for="email" class="form__label">Email address</label>
                    </div>

                    <div class="form__group">
                        <input type="password" class="form__input" placeholder="Password" id="password" autocomplete="new-password" required>
                        <label for="name" class="form__label">Password</label>
                    </div>

                    <div class="form__group u-margin-bottom-medium">
                        <div class="form__radio-group">
                            <input type="radio" class="form__radio-input" id="small" name="size">
                            <label for="small" class="form__radio-label">
                                <span class="form__radio-button"></span>
                               Remember me
                            </label>
                        </div>
                    </div>

                    <div class="form__group">
                        <button class="btn btn--green">Submit &rarr;</button>
                    </div>
                   
                   <a class="label" href="{{route('register')}}">Sign up instead</a>
                </form>
            </div>
        </div>
    </div>
</section>
</main>
  @endsection
  
