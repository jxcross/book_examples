var express = require('express');
var cors = require('cors')
var app = express();
var stringReplace = require('string-replace-middleware');
app.use(cors());

var KC_URL = process.env.KC_URL || "http://localhost:58081";
var SERVICE_URL = process.env.SERVICE_URL || "http://localhost:53000/secured";

app.use(stringReplace({
   'SERVICE_URL': SERVICE_URL,
   'KC_URL': KC_URL
}));
app.use(express.static('.'))

app.get('/', function(req, res) {
    res.render('index.html');
});


app.listen(58000);
