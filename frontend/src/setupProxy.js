const createProxyMiddleware = require("http-proxy-middleware");

// If running local dev server, change target below to match server addr.

module.exports = function (app) {
  app.use(
    "/api",
    createProxyMiddleware({
      target:  "https://orakelqueueservice.herokuapp.com", //"http://localhost:8080"
      changeOrigin: true,
    })
  );
};
