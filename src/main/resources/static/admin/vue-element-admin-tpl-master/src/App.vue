<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'app',
  created(){
  	var fullPath = this.$route.fullPath
  	if(fullPath == '/'){
  		this.$router.push({name: 'login'})
  	}
  }
}
</script>
