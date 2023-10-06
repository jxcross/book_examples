var express = require('express');
var cors = require('cors');

var app = express();
var stringReplace = require('string-replace-middleware');

//var KC_URL = "https://dev248.edison.re.kr:8543";
//var SERVICE_URL = process.env.SERVICE_URL || "http://localhost:53000/secured";
var KC_URL=process.env.KC_URL;
var SERVICE_URL=process.env.KC_SERVICE;
var KC_CLIENT=process.env.KC_CLIENT;
var KC_CLIENT_SECRET=process.env.KC_CLIENT_SECRET;

app.use(cors());
app.use(stringReplace({
   'SERVICE_URL': SERVICE_URL,
   'KC_URL': KC_URL,
   'KC_CLIENT': KC_CLIENT,
   'KC_CLIENT_SECRET': KC_CLIENT_SECRET
}));
app.use(express.static('.'))

app.get('/', function(req, res) {
    res.render('index.html');
});


app.listen(58000);
