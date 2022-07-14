<template>
<div>
  <div class="header">
    <h1 class="headerText">라우터연습</h1>
    <nav>
      <ul>
        <li><router-link to="/home">Home</router-link></li>
        <li><router-link to="/about">About</router-link></li>
        <li><router-link to="/contacts">Contacts</router-link></li>
      </ul>
    </nav>
  </div>

  <div class="container">
    <router-view></router-view>
  </div>
</div>
</template>
<script>
export default {
  name : 'app',
}
</script>
<style>
.header { background-color:rgb(188, 240, 240); padding: 10px 0px 0px 0px; }
.headerText { padding: 0px 20px 0px 20px; } 
ul { list-style-type: none; margin: 0; padding: 0;
    overflow: hidden; background-color: rgb(158, 156, 158);  }
li { float: left; }
li a { display: block; color: crimson; text-align: center;
    padding: 14px 16px; text-decoration: none;  }
li a:hover { background-color: rgb(199, 238, 238); color:black; }
</style>