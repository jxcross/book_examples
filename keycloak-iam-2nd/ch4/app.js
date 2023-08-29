var express = require('express');
var app = express();
var stringReplace = require('string-replace-middleware');

var KC_URL = process.env.KC_URL || "http://localhost:58081";

app.use(stringReplace({
   'KC_URL': KC_URL
}));
app.use(express.static('.'))

app.get('/', function(req, res) {
    res.render('index.html');
});


app.listen(58000);
