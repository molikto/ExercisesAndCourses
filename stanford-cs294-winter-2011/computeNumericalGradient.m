function numgrad = computeNumericalGradient(J, theta)
% numgrad = computeNumericalGradient(J, theta)
% theta: a vector of parameters
% J: a function that outputs a real-number. Calling y = J(theta) will return the
% function value at theta. 
  
eps = 10^-4;

%% ---------- YOUR CODE HERE --------------------------------------
% Instructions: 
% Implement numerical gradient checking, and return the result in numgrad.  
% (See Section 2.3 of the lecture notes.)
% You should write code so that numgrad(i) is (the numerical approximation to) the 
% partial derivative of J with respect to the i-th input argument, evaluated at theta.  
% I.e., numgrad(i) should be the (approximately) the partial derivative of J with 
% respect to theta(i).
%                
% Hint: You will probably want to compute the elements of numgrad one at a time. 

siz = size(theta, 1);
numgrad = zeros(siz,1);

for i = 1: siz
    d = [zeros(i - 1, 1); eps; zeros(siz - i, 1)];
    numgrad(i) = (J(theta + d) - J(theta - d)) / (2 * eps);
end

%% ---------------------------------------------------------------
end
