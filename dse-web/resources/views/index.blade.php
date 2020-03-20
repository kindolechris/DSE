@extends('layouts.app')

@section('content')
  <!-- Page Header -->




  
  <div class="navigation">

    <input type="checkbox" class="navigation__checkbox" id="navi-toggle">

    <label for="navi-toggle" class="navigation__button">
        <span class="navigation__icon">&nbsp;</span>
    </label>
    <div class="navigation__background">&nbsp;</div>
      <nav class="navigation__nav">
        <ul class="navigation__list" id="nav_list">
          <li class="navigation__item"><a href="{{route('login')}}" class="navigation__link"><span>01</span>Sign In</a></li>
          <li class="navigation__item"><a href="{{route('register')}}" class="navigation__link"><span>02</span>Sign Up</a></li>
            <li class="navigation__item"><a href="#" class="navigation__link"><span>03</span>Get in touch</a></li>
            <li class="navigation__item"><a href="#" class="navigation__link"><span>04</span>Frequent asked questions</a></li>
            <li class="navigation__item"><a href="#" class="navigation__link"><span>05</span>About us</a></li>
          </ul>
    </nav>
</div>

  <header class="header">
      <div class="header__logo-box">
        <img src="img/logo white.png" alt="logo" class="header__logo"/>
      </div>
      <div class="header__text-box">
        <h1 class="heading-primary">
          <span class="heading-primary--main">DSE tanzania</span>
          <span class="heading-primary--sub">Scholar investment challenge</span>
        </h1>

        <a href="" class="btn btn--white btn--animated">
          Discover our activities
        </a>
      </div>
  </header>

  <main>
    <section class="section-about">
      <div class="u-center-text u-margin-bottom-big">
       <h2 class="heading-secondary">How the framework works</h2>
      </div>
      <div class="row">
       <div class="col-1-of-2">
          <h3 class="heading-tertiary u-margin-bottom-small">Short title</h3>
           <p class="paragraph">
             Lorem ipsum dolor sit, amet consectetur adipisicing elit. Eius enim nostrum delectus, quaerat fugiat pariatur labore repellendus quae ex dolore dolor placeat repudiandae necessitatibus deleniti dolorum commodi eveniet dolores quam.
           </p>
           <h3 class="heading-tertiary u-margin-bottom-small">Short title</h3>
           <p class="paragraph">
             Lorem ipsum dolor sit, amet consectetur adipisicing elit. Eius enim nostrum delectus, quaerat fugiat pariatur labore repellendus quae ex dolore dolor.
           </p>
           <a href="#" class="btn-text">Learn more &rarr;</a>
         </div>
         <div class="col-1-of-2">
           <div class="composition">

               <img srcset="img/11.png 300w, img/nat-1-large.jpg 1000w"
                    sizes="(max-width: 56.25em) 20vw, (max-width: 37.5em) 30vw, 300px"
                    alt="Photo 1"
                    class="composition__photo composition__photo--p1"
                    src="img/11.png">

               <img srcset="img/11.png 300w, img/nat-2-large.jpg 1000w"
                    sizes="(max-width: 56.25em) 20vw, (max-width: 37.5em) 30vw, 300px"
                    alt="Photo 2"
                    class="composition__photo composition__photo--p2"
                    src="img/11.png">

               <img srcset="img/11.png 300w, img/nat-3-large.jpg 1000w"
                    sizes="(max-width: 56.25em) 20vw, (max-width: 37.5em) 30vw, 300px"
                    alt="Photo 3"
                    class="composition__photo composition__photo--p3"
                    src="img/11.png">
           </div>
       </div>
   </div>
    </section>

    <section class="section-features">
               
     <div class="row">
         <div class="col-1-of-4">
             <div class="feature-box">
                 <i class="feature-box__icon icon-basic-map"></i>
                 <h3 class="heading-tertiary u-margin-bottom-small">Invest</h3>
                 <p class="feature-box__text">
                     Lorem ipsum dolor sit amet consectetur adipisicing elit. Aperiam, ipsum sapiente aspernatur.
                 </p>
             </div>
         </div>

         <div class="col-1-of-4">
             <div class="feature-box">
                 <i class="feature-box__icon icon-basic-map"></i>
                 <h3 class="heading-tertiary u-margin-bottom-small">Decide</h3>
                 <p class="feature-box__text">
                     Lorem ipsum dolor sit amet consectetur adipisicing elit. Aperiam, ipsum sapiente aspernatur.
                 </p>
             </div>
         </div>

         <div class="col-1-of-4">
             <div class="feature-box">
                 <i class="feature-box__icon icon-basic-map"></i>
                 <h3 class="heading-tertiary u-margin-bottom-small">Extend Portfolio</h3>
                  <p class="feature-box__text">
                     Lorem ipsum dolor sit amet consectetur adipisicing elit. Aperiam, ipsum sapiente aspernatur.
                 </p>
             </div>
         </div>

         <div class="col-1-of-4">
             <div class="feature-box">
                 <i class="feature-box__icon icon-basic-map"></i>
                 <h3 class="heading-tertiary u-margin-bottom-small">Win</h3>
                 <p class="feature-box__text">
                     Lorem ipsum dolor sit amet consectetur adipisicing elit. Aperiam, ipsum sapiente aspernatur.
                 </p>
             </div>
         </div>
     </div>
 </section>
  </main>
  @endsection
  

