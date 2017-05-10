'''
Created on May 9, 2017
@author: Priyanka Samanta
@version: 1.0
'''
import sys
import csv

class CLASSIFICATION:
    
    #declaration of variables
    def __init__(self):
        self.start_bin_value=38
        self.end_bin_value=78
        self.bin_size=0.5
        self.histogram={}
        self.reckless_yes={}
        self.reckless_no={}
        self.N=0
        self.missClassificationRate={}
        self.truePositive={}
        self.falsePositive={}
        self.thresholds={}
        self.capacity=((self.end_bin_value-self.start_bin_value)*2)+1;
        for i in range(0,self.capacity):
            self.histogram[i]=0
            self.reckless_no[i]=0
            self.reckless_yes[i]=0
            self.missClassificationRate[i]=0
            self.thresholds[i]=0
            
        
    #reading data from from file   
    def readValue(self,fileName):
        fopen=open(fileName,"r")
        for line in fopen:
            
            #creating histogram
            if "SPEED" in line:
                continue
            self.N=self.N+1
            fInput=line.split(',')
            speed=float(fInput[0])
            is_reckless=int(fInput[1])
            i=0
            while(i<self.capacity):
                a=self.start_bin_value + (i*self.bin_size)
                b=self.start_bin_value + (i+1)*self.bin_size
                if( (speed>=a) and(speed < b)):
                    if i in self.histogram.keys():
                        self.histogram[i]=self.histogram[i]+1
                    else:
                        self.histogram[i]=1
                    if (is_reckless==0):
                        if i in self.reckless_no.keys():
                            self.reckless_no[i]=self.reckless_no[i]+1
                        else:
                            self.reckless_no[i]=1
                    if(is_reckless==1):
                        if i in self.reckless_yes.keys():
                            self.reckless_yes[i]=self.reckless_yes[i]+1
                        else:
                            self.reckless_yes[i]=1
                    break
                else:
                    i=i+1
                     
        fopen.close()
        
    # Classification algorithm   
    def classification(self):
        best_missclassification_rate=float('inf')
        threshold=float(0)
        n_miss_rate=float(0)
        n_fa_rate=float(0)
        
        for i in range (0,self.capacity):
            cutPointer=i
            ptrL=cutPointer
            ptrR=cutPointer+1
            sumL=0
            sumR=0
            n_miss=float(0)
            n_fa=float(0)
            n_tp=0
           
            #calculate the false alarm
            while (ptrR<self.capacity):
                sumR+=self.histogram[ptrR]
                n_fa+=self.reckless_no[ptrR]
                n_tp+=self.reckless_yes[ptrR]
                ptrR=ptrR+1

            if (sumR==0):
                n_fa_rate=0
            else:
                n_fa_rate=float(n_fa/sumR)
           
            #calculate no. of misses
            while (ptrL>=0):
                sumL+=self.histogram[ptrL]
                n_miss+=self.reckless_yes[ptrL]
                ptrL=ptrL-1
            if (sumL==0):
                n_miss_rate=0;
            else:
                n_miss_rate=float(n_miss/sumL)
            
            self.missClassificationRate[i]=float((n_fa_rate+n_miss_rate))
            self.truePositive[i]=n_tp
            self.falsePositive[i]=n_fa
            self.thresholds[i]=self.start_bin_value+(cutPointer*self.bin_size)
            
            #computing the best miss_classification rate and threshold
            if (best_missclassification_rate 
                > float((n_fa_rate+n_miss_rate))):
                best_missclassification_rate=float((n_fa_rate+n_miss_rate))
                threshold=(float) (self.start_bin_value+(cutPointer*self.bin_size));
                no_of_missed_reckless_driver=int(n_miss)
                no_of_non_reckless_driver_caught=int(n_fa)


        #printing the results
        #print("Best miss_classification rate : " +
        #       str(best_missclassification_rate))
        #print("No. of miss: " + str(no_of_missed_reckless_driver))
        #print("No. of false Alarm: " + str(no_of_non_reckless_driver_caught))
        print(str(threshold))
            
if __name__=="__main__":
    fileName=sys.argv[1]
    ob1=CLASSIFICATION()
    ob1.readValue(fileName)
    ob1.classification()
