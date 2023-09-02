import express from 'express';
import stringReplace from 'string-replace-middleware';

const app = express();
const port = 58080;

app.use(stringReplace({
  KC_URL: process.env.KC_URL || "http://localhost:58081"
}));

app.use('/', express.static('public'));

app.listen(port, () => {
  console.log(`Listening on port ${port}.`);
});
