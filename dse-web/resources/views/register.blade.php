@extends('layouts.app')

@section('content')
  <!-- Page Header -->

<main>
    <div class="navigation">
        <input type="checkbox" class="navigation__checkbox" id="navi-toggle">
        <a href="{{route('index')}}" for="navi-toggle" class="navigation__button"><span class="navigation__iconback"><i class="casualicon icon-basic-home"></i></span></a>
    
    </div>
 <section class="section-center">
    <div class="row">
        <div class="book">
            <div class="book__form">
                <form action="#" class="form">
                    <div class="u-margin-bottom-medium">
                        <h2 class="heading-secondary">
                           Register
                        </h2>
                    </div>
        
                    <div class="tab">
                        <h2 class="u-margin-bottom-medium">
                            Peronal info
                         </h2>
                        <div class="form__group">
                            <input type="text" class="form__input" placeholder="First name" id="name" autocomplete="new-name"  name="fname">
                            <label for="name" class="form__label">First Name</label>  
                        </div>
                          <div class="form__group">
                            <input type="text" class="form__input" placeholder="Last Name" id="name" autocomplete="new-name" name="lname">
                            <label for="name" class="form__label">Last Name</label>    
                        </div>
                          <div class="form__group u-margin-bottom-medium">
                            <div class="form__radio-group">
                                <input type="radio" class="form__radio-input" id="small" name="size">
                                <label for="small" class="form__radio-label">
                                    <span class="form__radio-button"></span>
                                   Male
                                </label>
                            </div>
                        </div>
                        
                        <div class="form__group u-margin-bottom-medium">
                            <div class="form__radio-group">
                                <input type="radio" class="form__radio-input" id="small" name="size">
                                <label for="small" class="form__radio-label">
                                    <span class="form__radio-button"></span>
                                   Female
                                </label>
                            </div>
                        </div>
    
                    </div> 

                    <div class="tab">
                        <h2 class="u-margin-bottom-medium">
                            Contact info
                         </h2>
                        <div class="form__group">
                            <input class="form__input" placeholder="Email address" id="email" autocomplete="new-email"  name="email">
                            <label for="email" class="form__label">Email Name</label>   
                        </div>
                          <div class="form__group">
                            <input class="form__input" placeholder="Phone number" id="phone" autocomplete="new-phone" name="phone">
                            <label for="phone" class="form__label">Phone Name</label>    
                        </div>
                    </div>

                    <div class="tab">
                        <h2 class="u-margin-bottom-medium">
                            Academic info
                         </h2>
                        <div class="form__group">
                            <input class="form__input" placeholder="University" id="university" autocomplete="new-university" name="university"> 
                            <label for="university" class="form__label">University</label>    
                        </div>
                        <div class="form__group">
                            <input class="form__input" placeholder="Course" id="course" autocomplete="new-course" name="course">
                            <label for="course" class="form__label">Course</label> 
                        </div>
                        <div class="form__group">
                            <input class="form__input" placeholder="Year" id="year" autocomplete="new-year" name="year">
                            <label for="year" class="form__label">Year</label> 
                        </div>
                    </div>
                   
                    <div class="tab">
                        <h2 class="u-margin-bottom-medium">
                            Contact info
                         </h2>
                        <div class="form__group">
                            <input class="form__input" placeholder="Email address" id="email" autocomplete="new-email" name="email">
                            <label for="email" class="form__label">Email</label>
                          </div>
    
                        <div class="form__group">
                            <input class="form__input" placeholder="Phone number" id="email" autocomplete="new-phone" name="phone">
                            <label for="phone" class="form__label">Phone</label>
                        </div>
                    </div>
                    
                    <div class="tab">
                        <h2 class="u-margin-bottom-medium">
                            Account info
                         </h2>
                        <div class="form__group">
                            <input class="form__input" placeholder="Trader name" id="tradername" autocomplete="new-name" name="name">
                            <label for="tradername" class="form__label">Trader name</label>  
                        </div>
                        <div class="form__group">
                            <input class="form__input" placeholder="Password" id="password" autocomplete="new-email" name="password">
                            <label for="password" class="form__label">Password</label>
                          </div>
                       
                        <div class="form__group">
                            <input class="form__input" placeholder="Confirm password" id="confirmpass" autocomplete="new-password"  name="confirmpassword">
                            <label for="confirmpass" class="form__label">Confirm password</label>
                        </div>
                    </div>
                                     
                    <div class="form__group">
                        <button type="button" class="btn btn--green" id="prevBtn" onclick="nextPrev(-1)">Previous &rarr;</button>&emsp;
                        <button type="button" class="btn btn--green" id="nextBtn" onclick="nextPrev(1)">Next &rarr;</button>
                    </div>

                      <!-- Circles which indicates the steps of the form: -->
                      <div style="text-align:center;margin-top:40px;">
                        <span class="step"></span>
                        <span class="step"></span>
                        <span class="step"></span>
                        <span class="step"></span>
                      </div>
                    
                   <a class="label" href="{{route('login')}}">Sign in instead</a>
                </form>
            </div>
        </div>
    </div>
</section>
</main>
  @endsection
  

