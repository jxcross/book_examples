var express = require('express');
var Keycloak = require('keycloak-connect');
var session = require('express-session');


var app = express();

/*----------------------------- memoryStore 
var memoryStore = new session.MemoryStore();
app.use(session({
    secret: 'some secret',
    resave: false,
    saveUninitialized: true,
    store: memoryStore
}));
var keycloak = new Keycloak({ store: memoryStore});
-----------------------------------------*/


var FileStore = require('session-file-store')(session);
var fileStoreOptions = {
  path: './session-data',      // 세션 데이터를 저장할 디렉터리
  ttl: 86400,                  // 세션 유효 기간을 24시간으로 설정
  reapInterval: 3600,          // 1시간마다 만료된 세션 체크
  retries: 5,                  // 파일 I/O 오류 발생 시 최대 5번 재시도
  fileExtension: '.sess'       // 세션 파일 확장자를 .sess로 설정
};
app.use(session({
  secret: 'some secret',
  resave: false,
  saveUninitialized: true,
  store: new FileStore(fileStoreOptions)
}));
//var keycloak = new Keycloak({ store: session.store });
var keycloak = new Keycloak({ store: FileStore });

//var keycloak = new Keycloak({});

app.use(keycloak.middleware());

app.get('/hello', keycloak.protect(), function (req, res) {
  console.log(keycloak);
  res.setHeader('content-type', 'text/plain');
  res.send('Access granted to protected resource');
});

app.listen(53002, function () {
  console.log('Started at port 53002');
});
