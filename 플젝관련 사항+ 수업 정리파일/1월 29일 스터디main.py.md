from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from datetime import datetime, timedelta, timezone
from jose import JWTError,jwt
from db import findAll,findOne,save
import mariadb
import uuid

SECRET_KEY = "09d25e094faa6ca2556c818166b7a9563b93f7099f6f0f4caa6cf63b88e8d3e7"
ALGORITHM = "HS256"
ACCESS_TOKEN_EXPIRE_MINUTES = 30


def set_token(no : int, name : str):
    try:
       iat = datetime.now(timezone.utc)
       exp = iat + timedelta(ACCESS_TOKEN_EXPIRE_MINUTES)
       
       data = {
          
          "name" : name,
          "iss" : "gayoung",
          "iat" : iat,
          "exp" : exp,
          "sub" : str(no)
        }

       return jwt.encode(data, key = SECRET_KEY, algorithm = ALGORITHM)

    except JWTError as e:
       print(f"JWTError : {e}")
    return None  





origins = [
    "http://localhost:5173",
   ]

class LoginModel(BaseModel):
  email : str
  pwd : str

app = FastAPI()

app.add_middleware(
    CORSMiddleware,
    allow_origins=origins,
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
def read_root():
  return {"Hello": "World"}

@app.post("/login")
def read_root(model: LoginModel):
    sql = f""" 
        select `no`,`name` from edu.user where `email` = '{model.email}' and `password` = '{model.pwd}'
        """
    data = findOne(sql)
    if data:
       id = uuid.uuid4().hex
       token = set_token(data["no"], data["name"])
       sql = f"INSERT INTO edu.login (`id`, `userNo`, `token`) value ('{id}', {data["no"]}, '{token}')"
       if save(sql):  
            return {"status":True, "token" : id}
    return {"status": False}
  
@app.get("/me")
def read_root(token):
   sql = f"select * from edu.login where `id` = '{token}'"
   data = findOne(sql)
   print(data)
   if data:
      payload = jwt.decode(data["token"], key = SECRET_KEY, algorithms = ALGORITHM)
    
   return {"status" : True, "data" : payload["name"]}
