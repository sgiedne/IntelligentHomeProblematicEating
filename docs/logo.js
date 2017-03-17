var canvas = document.getElementById("canvas");
var ctx = canvas.getContext("2d");
var cw = canvas.width = 500,
  cx = cw / 2;
var ch = canvas.height = 500,
  cy = ch / 2;
ctx.lineWidth = 2;
var rad = Math.PI / 180;
var frames = 0;
var points = [];
var a1 = -90 * rad;
var a2 = a1 + 120 * rad;
var a3 = a2 + 120 * rad;

var n = 7;
var R = 17 * n;
var c1 = getCenter(a1);
var c2 = getCenter(a2);
var c3 = getCenter(a3);

function getCenter(a) {
  return {
    x: cx + R * Math.cos(a),
    y: cy + R * Math.sin(a)
  }
}

function celtic1(c, rot) {
  for (var i = 0; i < 780; i += .5) {
    var o = {}
    o.t = -i * rad;
    o.R = -n * o.t;
    o.r = (500 / 780) * i * rad;
    o.x = c.x + o.R * Math.cos(o.t + rot);
    o.y = c.y + o.R * Math.sin(o.t + rot);
    o.color = "hsl(210,83%," + (20 + i / 11.7) + "%)"
    points.push(o);
  }
}

function celtic2(c, rot) {
  for (var i = 900; i > 0; i -= .5) {
    var o = {}
    o.t = -i * rad;
    o.R = n * o.t;
    o.r = (500 / 900) * i * rad;
    o.x = c.x + o.R * Math.cos(o.t + rot);
    o.y = c.y + o.R * Math.sin(o.t + rot);
    o.color = "hsl(210,83%," + (20 + i / 13.5) + "%)"
    points.push(o);
  }
}

celtic1(c1, 120 * rad);
celtic2(c2, -120 * rad);
celtic1(c2, -120 * rad);
celtic2(c3, 0 * rad);
celtic1(c3, 0 * rad);
celtic2(c1, 120 * rad);

function Draw() {
  elId = window.requestAnimationFrame(Draw);
  if (frames < 4 * n) {
    frames += 2;
  } else {
    frames = 0;
  }
  ctx.clearRect(0, 0, cw, ch);

  for (var i = 0; i < points.length; i += 4 * n) {
    var k = i + frames;
    var r = Math.max(Math.abs(points[k].r), 1)*n/5;
    
    ctx.strokeStyle = points[k].color;
    ctx.beginPath();
    ctx.strokeRect(points[k].x, points[k].y, r, r);
    
  }
}
elId = window.requestAnimationFrame(Draw);