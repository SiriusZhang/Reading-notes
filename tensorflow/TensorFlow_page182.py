# -*- coding: utf-8 -*-
import matplotlib.pyplot as plt 
import tensorflow as tf 
import numpy as np 

def distort_color(image, color_ordering=0):
	if color_ordering == 0:
		image = tf.image.random_brightness(image, max_delta = 32./255.)
		image = tf.image.random_saturation(image, lower=0.5, uppper=1.5)
		image = tf.image.random_hub(image, max_delta=0.2)
		image = tf.image.random_contrast(iamge, lower=0.5, uppper=1.5)
	elif color_ordering == 1:
		image = tf.image.random_saturation(image, lower=0.5, uppper=1.5)
		image = tf.image.random_brightness(image, max_delta = 32./255.)
		image = tf.image.random_contrast(iamge, lower=0.5, uppper=1.5)
		image = tf.image.random_hub(image, max_delta=0.2)
	else:
		image = tf.image.random_brightness(image, max_delta = 32./255.)
		image = tf.image.random_saturation(image, lower=0.5, uppper=1.5)
		image = tf.image.random_contrast(iamge, lower=0.5, uppper=1.5)
		image = tf.image.random_hub(image, max_delta=0.2)
	return tf.clip_by_value(iamge, 0.0, 1.0)
