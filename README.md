# SpeckedDetect
Silicon Valley Hacks 4th place. An Android App that uses a Tensorflow model trained in Python to detect Diabetic Retinopathy.

## Inspiration
According to the National Eye Institute, the number of people with retinopathy diabetes is expected to double in the next 30 years. This disease causes pain in the eyes and may lead to blindness and it can be better treated if detected and diagnosed earlier.

## What it does
Given an image of a retina taken under a lens the app will determine if the subject has retinopathy diabetes.

## How we built it
We first downloaded data from Kaggle and formatted it and fed it to a convolutional neural network that we wrote using Tensorflow in Python. We converted the trained network to a .pb file so we could use it in our android app.

## Challenges we ran into
We had a lot of issues when running the convolutional neural network and had to deal with a ton of errors. Our accuracies were initially super low and it was also hard to get the .pb file to work in our android program. It also took a lot of time to train the network each time so we had to wait 20 to 25 minutes or so each time we tried to train it.

## Accomplishments that we're proud of
We are proud of having the app be fully functional despite the many challenges we have. We are proud that our app may help people who have retinopathy diabetes. We are proud of being able to integrate a neural network in Python into Android.

## What we learned
We learnt a lot about TensorFlow and convolutional neural networks in Python. We learnt a lot about the disease of retinopathy diabetes. We learnt a lot about how to use neural networks in Android and how to build an Android app.

## What's next for SpeckedDetect
We plan to use more data to train our convolutional neural network to make it more accurate. We want to try data from other sources and datasets to reduce the risk of overfitting to our dataset from Kaggle. We also want to actually release our app on the Google Play Store and eventually develop the app on the App Store.

Learn more about our project at the [Devpost link](https://devpost.com/software/speckeddetect)
