<template>
	<div class="navbar">
        <div class="container-fluid">
            <div>
                <div>

                    <div class="navbar-header">
                        <button class="navbar-toggle" data-target="#mobile_menu" data-toggle="collapse"><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button>
                        <a href="#" class="navbar-brand">GROUP.COM</a>
                    </div>

                    <div class="navbar-collapse collapse" id="mobile_menu">
                        <ul class="nav navbar-nav">
                            <li class="active"><a href="#">Home</a></li>
                            <li><a href="#">Welcome</a></li>
                            <li><a href="#">Services</a></li>
                            <li><a href="#">Gallery</a></li>
                            <li><a href="#">Contact Us</a></li>
                        </ul>
                        <ul class="nav navbar-nav">
                        </ul>

                        <ul class="nav navbar-nav navbar-right">
                            <li><a href="#"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
                            <li><a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-log-in"></span> Login / Sign Up <span class="caret"></span></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {mapGetters} from 'vuex'

export default {
  name: "Navbar2",
  computed: {
    ...mapGetters([
      'isLoggedIn',
    ]),
    needHide() {
      return !(this.$route.name === 'Signup1' || this.$route.name === 'Signup2' || this.$route.name === 'Login');
    },
  },
};
</script>

<style>
/* .navbar {
  align-items: center;
  font-family: 'NanumSquare', sans-serif;
  font-size: 15px;
  height: 6rem;
  position: fixed;
  width: 100%;
  z-index: 100;
} */

/* Appearance */
.links {
	background-color: white;
	/* background-image: linear-gradient(to bottom, #B5C7D3, transparent); */
	/* border-bottom: 0.1px solid #B5C7D3; */
	/* box-shadow: 0 0 01px #658DC6; */
	font-size: 0.9em;
	font-weight: 400;
  align-items: center;
}
.links > a {
	color: black;
	text-align: center;
	text-decoration: none;
	transition: all .5s;
}
/* .links > a:hover {
	background: #0F4C81;
	color: white;
} */

.links > .line {
	background: #0F4C81;
	height: 4px;
	pointer-events: none;
}

/* The Magic */
.links {
	display: grid;
	grid-template-columns: repeat(var(--items), 1fr);
	position: relative;
}
.links > .line {
	opacity: 0;
	transition: all .5s;
	position: absolute;
	bottom: 0;
	left: var(--left, calc(100% / var(--items) * (var(--index) - 1)));
	width: var(--width, calc(100% / var(--items)));
	--index: 0;
}
.links > a:hover ~ .line {
	opacity: 1;
}

.links > a:nth-of-type(1):hover ~ .line { --index: 1; }
.links > a:nth-of-type(2):hover ~ .line { --index: 2; }
.links > a:nth-of-type(3):hover ~ .line { --index: 3; }
.links > a:nth-of-type(4):hover ~ .line { --index: 4; }
.links > a:nth-of-type(5):hover ~ .line { --index: 5; }
.links > a:nth-of-type(6):hover ~ .line { --index: 6; }
.links > a:nth-of-type(7):hover ~ .line { --index: 7; }
.links > a:nth-of-type(8):hover ~ .line { --index: 8; }
.links > a:nth-of-type(9):hover ~ .line { --index: 9; }
.links > a:nth-of-type(10):hover ~ .line { --index: 10; }
.links > a:last-of-type:hover ~ .line { --index: var(--items); }
</style>
