function res = addDummyOnes(M)
  res = [ones(size(M,1),1) M];
endfunction

v = zeros(10,1)

for i=1:10;
  v(i) = 2 ^ i;
end;

indices=1:10

for i=indices;
  v(i) = 2 ^ i;
end;

% While Loop

i=1;
while i <= 5;
  v(i) = 100;
  i = i + 1;
end;

i = 1;
while true;
  v(i) = 999;
  i = i + 1;
  if i == 6;
    break;
  end;
end;

% If Else

v(1) = 2;
if v(1) == 1,
  disp('The value is one');
elseif v(1) == 2,
  disp('The value is two');
else,
  disp('The velue is neither one nor two');
end;

% exit or quit to quit CLI

% addpath('path') to add to the search path
% functionname.m file for functionname

X = [1 1; 1 2; 1 3]
y = [1; 2; 3]

function J = costFunction(X, y, theta)
    m = size(X, 1);  % number of training examples
    predictions = X * theta;
    sqrErrors = (predictions - y) .^ 2;
    J = 1/(2*m) * sum(sqrErrors);
endfunction;

function d = diffTheta(X, y, theta, alpha)
    m = size(X, 1);
    errors = (X * theta) - y;
    errors_times_X = X .* errors;
    sumErrors = sum(errors_times_X);
    d1 = sumErrors * (alpha / m);
    d = d1';
endfunction;

thetas = [0;0];
alpha = 0.1;
maxTrials = 500;
i = 1;
times = (1:maxTrials)';
costs = zeros(maxTrials, 1);
while i <= maxTrials,
    j = costFunction(X, y, thetas);
    costs(i) = j;
    deltas = diffTheta(X, y, thetas, alpha);
    thetas = thetas - deltas;
    i = i + 1;
end;

function [nthetas, times, costs] = runGradientDescent(X, y, thetas, alpha, maxTrials),
    i = 1;
    times = (1:maxTrials)';
    costs = zeros(maxTrials, 1);
    while i <= maxTrials,
        j = costFunction(X, y, thetas);
        costs(i) = j;
        deltas = diffTheta(X, y, thetas, alpha);
        thetas = thetas - deltas;
        i = i + 1;
    end;
    nthetas = thetas;
endfunction;

[nthetas, times, cost] = runGradientDescent(X, y, [0;0], .1, 2000);

tic(); [nthetas, times, cost] = runGradientDescent(X, y, [0;0], .1, 2000); toc();

function nthetas = normalFunction(X, y),
    nthetas = pinv(X' * X) * X' * y;
endfunction;

tic(); nthetas = normalFunction(X, y); toc();
