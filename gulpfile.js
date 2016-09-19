var gulp = require('gulp'),
	sass = require('gulp-sass'),
	notify = require("gulp-notify"),
	bower = require('gulp-bower');

var config = {
	sassPath: './src/main/resources/sass',
	bowerDir: './bower_components',
	resources: 'src/main/webapp/resources'
}

gulp.task('bower', function() {
	return bower()
	       	.pipe(gulp.dest(config.bowerDir));
});

gulp.task('icons', function() {
	return gulp
		.src(config.bowerDir + '/fontawesome/fonts/**.*')
		.pipe(gulp.dest(config.resources + '/fonts'));
});

gulp.task('glyphicons', function() {
	return gulp
		.src(config.bowerDir + '/bootstrap/fonts/**.*')
		.pipe(gulp.dest(config.resources + '/fonts/bootstrap'));
});

gulp.task('js', function() {
	return gulp
		.src([
			config.bowerDir + '/jquery/dist/jquery.min.js',
			config.bowerDir + '/bootstrap/dist/js/bootstrap.min.js'
		])
		.pipe(gulp.dest(config.resources + '/js'));
})

gulp.task('css', function() {
    return gulp.src(config.sassPath + '/style.scss')
    .pipe(sass({
    	outputStyle: 'compressed',
        includePaths: [
        	config.bowerDir + '/bootstrap-sass/assets/stylesheets',
        	config.bowerDir + '/font-awesome/scss'
        	]
    	})
        .on("error", notify.onError(function(error) {
        	return "Error: " + error.message;
        }))
    )
    .pipe(gulp.dest('src/main/webapp/resources/css'));
});

gulp.task('watch', function() {
	gulp.watch(config.sassPath + '**/*.scss', ['css']);
});

gulp.task('default', ['bower', 'icons', 'glyphicons', 'js', 'css']);
