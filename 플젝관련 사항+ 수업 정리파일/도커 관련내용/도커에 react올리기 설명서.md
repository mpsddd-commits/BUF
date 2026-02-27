# 1.폴더 하나를 만든다
# 2. 리액트 만들기 + 설정
```bash
npm create vite .
npm i
npm i react-router-dom
npm i axios
npm i bootstrap bootstrap-icons
npm i react-cookie
```

-설정 후 빌드 하는것 ( 기초 설정 후 함 해봐라)

```bash
npm run build
```



- React 기본 설정 `app.jsx`

```js
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
import { BrowserRouter, Routes, Route } from "react-router";

const Home = () => {
  return (
    <div className="text-center">
      <h1>메인 화면입니다.</h1>
    </div>
  )
}

const NotFound = () => {
  return (
    <div className="text-center">
      <h1>404</h1>
      <p>페이지를 찾을 수 없습니다.</p>
    </div>
  )
}

const App = () => {
  const paths = [
    {path: "/", element: <Home />},
    {path: "*", element: <NotFound />},
  ]
  return (
    <>
      <BrowserRouter>
        <Routes>
          { paths?.map((v, i) => <Route key={i} path={v.path} element={v.element} />) }
        </Routes>
      </BrowserRouter>
    </>
  )
}

export default App
```

- `vite.config.js` 설정

```bash

import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import { fileURLToPath, URL } from 'url'

// https://vite.dev/config/
export default defineConfig({
     plugins: [react()],
     base: "/",
     server: {
     host: true,
   },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url)),
      '@pages': fileURLToPath(new URL('./src/pages', import.meta.url)),
      '@styles': fileURLToPath(new URL('./src/styles', import.meta.url)),
      '@assets': fileURLToPath(new URL('./src/assets', import.meta.url)),
      '@hooks': fileURLToPath(new URL('./src/hooks', import.meta.url)),
    }
  }
})
```

만든 폴더 제일 상위에 default.conf 생성
```bash
server {
    listen       80;
    listen  [::]:80;
    server_name  localhost;

    #access_log  /var/log/nginx/host.access.log  main;

    root   /usr/share/nginx/html;
    index  index.html index.htm;

    location / {
        # root   /usr/share/nginx/html;
        # index  index.html index.htm;
        try_files $uri /index.html;
    }

    #error_page  404              /404.html;

    # redirect server error pages to the static page /50x.html
    #
    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   /usr/share/nginx/html;
    }

    # proxy the PHP scripts to Apache listening on 127.0.0.1:80
    #
    #location ~ \.php$ {
    #    proxy_pass   http://127.0.0.1;
    #}

    # pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
    #
    #location ~ \.php$ {
    #    root           html;
    #    fastcgi_pass   127.0.0.1:9000;
    #    fastcgi_index  index.php;
    #    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
    #    include        fastcgi_params;
    #}

    # deny access to .htaccess files, if Apache's document root
    # concurs with nginx's one
    #
    #location ~ /\.ht {
    #    deny  all;
    #}
}
```

만든 폴더 제일 상위에 dockerfile 생성
```bash
FROM node:24.13.0 as build

WORKDIR /app

COPY package.json ./
RUN npm install 

COPY . .

RUN npm run build

FROM nginx:1.28

COPY --from=build /app/dist /usr/share/nginx/html
COPY ./default.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]
```

docker 명령어 모음집

```bash
docker build -t react:0.1 .
docker images
docker run -d -p 80:80 --name frontend react:0.1

docker rm -f frontend
```


