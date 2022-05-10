module.exports = {
    baseUrl: './',
    assetsDir: 'static',
    productionSourceMap: false,
    devServer: {
        proxy: {
            '^/api': {
                // 改成你后端服务的ip+端口
                target: 'http://localhost:8001',
                changeOrigin: true
            }
        },
        disableHostCheck: true,
    }
};