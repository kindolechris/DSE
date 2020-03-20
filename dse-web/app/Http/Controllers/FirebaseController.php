<?php
    namespace App\Http\Controllers;
    use Illuminate\Http\Request;
    use Kreait\Firebase;
    use Kreait\Firebase\Factory;
    use Kreait\Firebase\ServiceAccount;
    use Kreait\Firebase\Database;

    class FirebaseController extends Controller
    {
    //
    public function test(){
    $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/dse-tz-388dd36076a1.json');
    $firebase = (new Factory)
    ->withServiceAccount($serviceAccount)
    ->withDatabaseUri('https://dse-tz.firebaseio.com/')
    ->create();
    $database = $firebase->getDatabase();
    $newPost = $database
    ->getReference('Bonds')
    ->push([
    'board' => 'NMB',
    'price' => '500'
    ]);
    //$newPost->getKey(); // => -KVr5eu8gcTv7_AHb-3-
    //$newPost->getUri(); // => https://my-project.firebaseio.com/blog/posts/-KVr5eu8gcTv7_AHb-3-
    //$newPost->getChild('title')->set('Changed post title');
    //$newPost->getValue(); // Fetches the data from the realtime database
    //$newPost->remove();
    echo"<pre>";
    print_r($newPost->getvalue());
    }

    public function createUser(){
        $serviceAccount = ServiceAccount::fromJsonFile(__DIR__.'/dse-tz-388dd36076a1.json');
        $auth = (new Factory)
    ->withServiceAccount($serviceAccount)
    ->createAuth();

    $userProperties = [
        'email' => 'user@example.com',
        'emailVerified' => false,
        'phoneNumber' => '+15555550100',
        'password' => 'secretPassword',
        'displayName' => 'John Doe',
        'photoUrl' => 'http://www.example.com/12345678/photo.png',
        'disabled' => false,
    ];
    
    $createdUser = $auth->createUser($userProperties);
    

    }
}
    ?>