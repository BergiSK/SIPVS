$(document).ready(function(){
    $('#firstName').on('input', function() {
        var input=$(this);
//                var mailPattern = new RegExp('^[a-z0-9_\\-\\.]{2,}@[a-z0-9_\\-\\.]{2,}\\.[a-z]{2,}$');
        if (input.val().length === 0) {
            input.removeClass("valid").addClass("invalid");
        } else{
            input.removeClass("invalid").addClass("valid");
        }
    });

    $('#lastName').on('input', function() {
        var input=$(this);
        if (input.val().length === 0) {
            input.removeClass("valid").addClass("invalid");
        } else{
            input.removeClass("invalid").addClass("valid");
        }
    });

    $('#age').on('input', function() {
        var input=$(this);
        var agePattern = new RegExp('^[0-9]{1,2}$');

        if (agePattern.test(input.val())) {
            input.removeClass("invalid").addClass("valid");
        } else{
            input.removeClass("valid").addClass("invalid");
        }
    });

    $('#team').on('input', function() {
        var input=$(this);
        if (input.val().length === 0) {
            input.removeClass("valid").addClass("invalid");
        } else{
            input.removeClass("invalid").addClass("valid");
        }
    });

    $('#shortcut').on('input', function() {
        var input=$(this);
        if (input.val().length === 0) {
            input.removeClass("valid").addClass("invalid");
        } else{
            input.removeClass("invalid").addClass("valid");
        }
    });

    $('#player1').on('input', function() {
        var input=$(this);
        if (input.val().length === 0) {
            input.removeClass("valid").addClass("invalid");
        } else{
            input.removeClass("invalid").addClass("valid");
        }
    });

    $('#player2').on('input', function() {
        var input=$(this);
        if (input.val().length === 0) {
            input.removeClass("valid").addClass("invalid");
        } else{
            input.removeClass("invalid").addClass("valid");
        }
    });

    $('#player3').on('input', function() {
        var input=$(this);
        if (input.val().length === 0) {
            input.removeClass("valid").addClass("invalid");
        } else{
            input.removeClass("invalid").addClass("valid");
        }
    });
});